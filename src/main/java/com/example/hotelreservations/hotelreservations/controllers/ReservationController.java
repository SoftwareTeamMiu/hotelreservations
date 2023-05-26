package com.example.hotelreservations.hotelreservations.controllers;

import com.example.hotelreservations.hotelreservations.models.PaymentMethods;
import com.example.hotelreservations.hotelreservations.models.Reservation;
import com.example.hotelreservations.hotelreservations.models.ReservationStatus;
import com.example.hotelreservations.hotelreservations.models.Room;
import com.example.hotelreservations.hotelreservations.models.User;
import com.example.hotelreservations.hotelreservations.services.JwtService;
import com.example.hotelreservations.hotelreservations.services.PaymentMethodsService;
import com.example.hotelreservations.hotelreservations.services.ReservationService;
import com.example.hotelreservations.hotelreservations.services.ReservationStatusService;
import com.example.hotelreservations.hotelreservations.services.RoomServices;
import com.example.hotelreservations.hotelreservations.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    RoomServices roomServices;

    @Autowired
    UserService userService;

    @Autowired
    PaymentMethodsService paymentMethodsService;

    @Autowired
    ReservationStatusService reservationStatusService;

    @Autowired
    JwtService jwtService;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @PostMapping("")
    public ResponseEntity createReservation(HttpServletRequest request, @RequestBody Map<String, Object> request_body) {
        try {
            Reservation reservation = new Reservation();
            User user = new User();
            ReservationStatus reservationStatus = new ReservationStatus();
            PaymentMethods paymentMethods = new PaymentMethods();
            List<Room> rooms = new ArrayList<Room>();
            double total_price = 0;
            //
            String start_string_date = (String) request_body.get("reservation_start_date");
            Date start_date = dateFormat.parse(start_string_date);
            reservation.setStart_date(start_date);
            //
            String end_string_date = (String) request_body.get("reservation_end_date");
            Date end_date = dateFormat.parse(end_string_date);
            reservation.setStart_date(end_date);

            String token = (request.getHeader(HttpHeaders.AUTHORIZATION)).substring(7);
            String Userid = jwtService.extractUUID(token);
            user = userService.getUserById(Userid);
            //
            String reservationstatusID = (String) request_body.get("reservation_status_id");
            reservationStatus = reservationStatusService
                    .getReservationStatusById(Integer.parseInt(reservationstatusID));
            //
            String paymentmethodID = (String) request_body.get("payment_method_id");
            paymentMethods = paymentMethodsService.getPaymentMethodByID(Integer.parseInt(paymentmethodID));

            List<Integer> room_ids = (List<Integer>) request_body.get("rooms");
            for (int i = 0; i < room_ids.size(); i++) {
                Room room = new Room();
                room = roomServices.getRoomById(room_ids.get(i));
                rooms.add(room);
            }
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getOffer() != null) {
                    double room_price = rooms.get(i).getOffer().getPercentage() * rooms.get(i).getPrice();
                    total_price += room_price;
                } else {
                    total_price += rooms.get(i).getPrice();
                }
            }

            reservation.setStart_date(start_date);
            reservation.setEnd_date(end_date);
            reservation.setCustomer(user);
            reservation.setRooms(rooms);
            reservation.setPaymentMethods(paymentMethods);
            reservation.setReservationStatus(reservationStatus);
            reservation.setTotal_price(total_price);

            boolean hasOverlappingReservations = reservationService.hasOverlappingReservations(reservation.getRooms(),
                    reservation.getStart_date(),
                    reservation.getEnd_date());
            if (hasOverlappingReservations) {
                return ResponseEntity.badRequest().body("there is rooms are already booked for the given dates");
            }

            reservationService.createReservation(reservation);
            System.out.println("Done");

            return ResponseEntity.ok().body("Reservation Created Successfully");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error Creating Reservation," + e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity getAllReservations() {
        try {
            List<Reservation> reservations = reservationService.getAllReservations();
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error getting reservations: " + e.getMessage());
        }
    }

    @GetMapping("/{reservationID}")
    public ResponseEntity getReservationById(@PathVariable int reservationID) {
        try {
            Reservation reservation = reservationService.getReservationByID(reservationID);
            List<Reservation> reservations = new ArrayList<Reservation>();
            reservations.add(reservation);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error getting reservation by id: " + e.getMessage());
        }
    }

    @DeleteMapping("/{reservationID}")
    public ResponseEntity deleteReservationByID(@PathVariable int reservationID) {
        try {
            Reservation reservation = reservationService.getReservationByID(reservationID);
            if (reservation != null) {
                reservationService.deleteReservationByID(reservationID);
                return ResponseEntity.ok("Reservation deleted successfully");

            } else {
                return ResponseEntity.badRequest().body("Reservation not found");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting Reservation: " + e.getMessage());
        }
    }

    @PutMapping("/{ReservationID}")
    public ResponseEntity updateReservationID(@PathVariable int ReservationID,
            @RequestBody Map<String, Object> request_body, HttpServletRequest request) {
        try {
            User user = new User();
            String token = (request.getHeader(HttpHeaders.AUTHORIZATION)).substring(7);
            String Userid = jwtService.extractUUID(token);
            user = userService.getUserById(Userid);
            //
            Reservation reservation = reservationService.getReservationByID(ReservationID);
            if (reservation != null) {
                if (request_body.get("reservation_end_date") != null) {
                    String end_string_date = (String) request_body.get("reservation_end_date");
                    Date end_date = dateFormat.parse(end_string_date);
                    reservation.setStart_date(end_date);
                }
                if (request_body.get("start_date") != null) {
                    String start_string_date = (String) request_body.get("reservation_start_date");
                    Date start_date = dateFormat.parse(start_string_date);
                    reservation.setStart_date(start_date);
                }
                if (request_body.get("rooms") != null) {
                    List<Room> rooms = new ArrayList<Room>();
                    double total_price = 0;
                    List<Integer> room_ids = (List<Integer>) request_body.get("rooms");
                    for (int i = 0; i < room_ids.size(); i++) {
                        Room room = new Room();
                        room = roomServices.getRoomById(room_ids.get(i));
                        rooms.add(room);
                    }
                    reservation.setRooms(rooms);
                    for (int i = 0; i < rooms.size(); i++) {
                        if (rooms.get(i).getOffer() != null) {
                            double room_price = rooms.get(i).getOffer().getPercentage() * rooms.get(i).getPrice();
                            total_price += room_price;
                        } else {
                            total_price += rooms.get(i).getPrice();
                        }
                    }
                    reservation.setTotal_price(total_price);
                }
                if (request_body.get("payment_methods_id") != null) {
                    PaymentMethods paymentMethods = new PaymentMethods();
                    int paymentmethodID = (Integer) request_body.get("payment_method_id");
                    paymentMethods = paymentMethodsService.getPaymentMethodByID(paymentmethodID);
                    reservation.setPaymentMethods(paymentMethods);
                }
                if (request_body.get("reservation_status_id") != null) {
                    ReservationStatus reservationStatus = new ReservationStatus();
                    String reservationstatusID = (String) request_body.get("reservation_status_id");
                    reservationStatus = reservationStatusService
                            .getReservationStatusById(Integer.parseInt(reservationstatusID));
                    reservation.setReservationStatus(reservationStatus);
                }
                reservationService.createReservation(reservation);
                return ResponseEntity.ok("Reservation updated successfully!");
            } else {
                return ResponseEntity.badRequest().body("Reservation not found");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating Reservation: " + e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity getAllReservationsByUser(HttpServletRequest request) {
        try {
            String token = (request.getHeader(HttpHeaders.AUTHORIZATION)).substring(7);
            String Userid = jwtService.extractUUID(token);
            User user = userService.getUserById(Userid);
            List<Reservation> reservations = reservationService.getReservationByUserID(user.getId());
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error getting reservations: " + e.getMessage());
        }
    }
}
