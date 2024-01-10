package ru.sber.delivery.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import ru.sber.delivery.entities.User;
import ru.sber.delivery.services.AdministrationService;
import ru.sber.delivery.services.JwtService;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.http.*;

/**
 * Класс отвечающий за обработку запросов администраторов
 */
@Slf4j
@RestController
@RequestMapping("administrators")
public class AdministratorController {
    private final AdministrationService administratorService;
    private final JwtService jwtService;

    /**
     * Конструктор контроллера администраторов
     */
    @Autowired
    public AdministratorController(AdministrationService administratorService, JwtService jwtService) {
        this.administratorService = administratorService;
        this.jwtService = jwtService;
    }

    /**
     * Возвращает информацию о курьере
     *
     * @param idUser - индификатор курьера
     * @return - данные о курьере
     */
    @GetMapping("/courier/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<?> getCouriersData(@PathVariable("id") String idUser) {
        log.info("Получение информации о курьере");
        Jwt jwtToken = jwtService.getUserJwtTokenSecurityContext(); 

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken.getTokenValue());

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        String apiUrl = "http://localhost:8080/admin/realms/delivery-realm/users/" + idUser;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl, HttpMethod.GET, requestEntity, String.class);
    
            return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Обновляет информацию о курьере
     *
     * @param user - новая информация о пользователе
     * @return - результат запроса
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<?> updateCourier(@PathVariable("id") String idUser, @RequestBody Object object) {
        log.info("Обновление данных о курьере");
        Jwt jwtToken = jwtService.getUserJwtTokenSecurityContext(); 

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken.getTokenValue());

        HttpEntity<Object> requestEntity = new HttpEntity<>(object, headers);

        String apiUrl = "http://localhost:8080/admin/realms/delivery-realm/users/" + idUser;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl, HttpMethod.PUT, requestEntity, String.class);

            return new ResponseEntity<>(responseEntity.getStatusCode());
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Удаляет курьера
     *
     * @param idUser - индификатор пользователя
     * @return - результат запроса
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourier(@PathVariable("id") String idUser) {
        log.info("Удаление курьера");
        User user = new User();
        user.setId(idUser);
        boolean isDeleted = administratorService.delete(user);

        if (isDeleted) {
            Jwt jwtToken = jwtService.getUserJwtTokenSecurityContext(); 

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(jwtToken.getTokenValue());

            HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

            String apiUrl = "http://localhost:8080/admin/realms/delivery-realm/users/" + idUser;

            try {
                ResponseEntity<String> responseEntity = restTemplate.exchange(
                    apiUrl, HttpMethod.DELETE, requestEntity, String.class);

                return new ResponseEntity<>(responseEntity.getStatusCode());
            } catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Получает информацию о всех курьерах
     *
     * @return - список курьеров
     */
    @GetMapping("/all-couriers")
    public ResponseEntity<?> getAllCouriersData() {
        log.info("Получение информацию о курьерах");

        Jwt jwtToken = jwtService.getUserJwtTokenSecurityContext(); 

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken.getTokenValue());

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        String apiUrl = "http://localhost:8080/admin/realms/delivery-realm/users/";

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl, HttpMethod.GET, requestEntity, String.class);
    
            return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Получает информацию о всех курьерах вышедших на смену в заданный день
     *
     * @return - список курьеров
     */
    // @GetMapping("/all-couriers/by-date")
    // public ResponseEntity<List<User>> getAllShifts(@RequestParam("shiftDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate shiftDate) {
    //     log.info("Получение информации о всех пользователях вышедших на смену в заданный день");
    //     System.out.println(shiftDate);
    //     return ResponseEntity.ok().body(administratorService.findUsersByShift(shiftDate));
    // }

}
