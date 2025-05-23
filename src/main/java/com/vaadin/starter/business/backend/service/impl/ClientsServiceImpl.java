package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.Client;
import com.vaadin.starter.business.backend.service.ClientsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Implementation of the ClientsService interface.
 */
@Service
public class ClientsServiceImpl implements ClientsService {

    private final Map<Long, Client> clients = new HashMap<>();
    private final Random random = new Random(1);

    private static final String[] FIRST_NAMES = new String[]{
            "James", "John", "Robert", "Michael", "William", "David", "Richard", "Joseph",
            "Thomas", "Charles", "Mary", "Patricia", "Jennifer", "Linda", "Elizabeth",
            "Barbara", "Susan", "Jessica", "Sarah", "Karen"
    };

    private static final String[] LAST_NAMES = new String[]{
            "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson",
            "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris",
            "Martin", "Thompson", "Garcia", "Martinez", "Robinson"
    };

    private static final String[] EMAIL_DOMAINS = new String[]{
            "gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "aol.com",
            "icloud.com", "protonmail.com", "mail.com", "zoho.com", "yandex.com"
    };

    private static final String[] STREET_NAMES = new String[]{
            "Main St", "Oak St", "Maple Ave", "Washington St", "Park Ave",
            "Elm St", "High St", "Cedar Rd", "Lake St", "Pine St"
    };

    private static final String[] CITIES = new String[]{
            "New York", "Los Angeles", "Chicago", "Houston", "Phoenix",
            "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose"
    };

    private static final String[] STATES = new String[]{
            "NY", "CA", "IL", "TX", "AZ", "PA", "TX", "CA", "TX", "CA"
    };

    /**
     * Constructor that initializes the clients data.
     */
    public ClientsServiceImpl() {
        initClients();
    }

    /**
     * Initialize the clients data.
     */
    private void initClients() {
        int startingPoint = 2000;
        for (long i = 0; i < 40; i++) {
            clients.put(i + startingPoint,
                    new Client(i + startingPoint, getFullName(), getEmail(), getPhone(),
                            getAddress(), getRandomDouble(1000, 50000),
                            getDate(), getImageSource()));
        }
    }

    @Override
    public Collection<Client> getClients() {
        return clients.values();
    }

    @Override
    public Client getClient(Long id) {
        return clients.get(id);
    }

    private String getFullName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)] + " " + 
               LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }

    private String getEmail() {
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)].toLowerCase();
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)].toLowerCase();
        String domain = EMAIL_DOMAINS[random.nextInt(EMAIL_DOMAINS.length)];
        return firstName + "." + lastName + "@" + domain;
    }

    private String getPhone() {
        return String.format("(%03d) %03d-%04d", 
                random.nextInt(800) + 200, 
                random.nextInt(800) + 200, 
                random.nextInt(10000));
    }

    private String getAddress() {
        int houseNumber = random.nextInt(9000) + 1000;
        String street = STREET_NAMES[random.nextInt(STREET_NAMES.length)];
        String city = CITIES[random.nextInt(CITIES.length)];
        String state = STATES[random.nextInt(STATES.length)];
        int zipCode = random.nextInt(90000) + 10000;
        return houseNumber + " " + street + ", " + city + ", " + state + " " + zipCode;
    }

    private LocalDate getDate() {
        return LocalDate.now().minusDays(random.nextInt(1000));
    }

    private Double getRandomDouble(int min, int max) {
        return min + (max - min) * random.nextDouble();
    }

    private String getImageSource() {
        return "images/avatars/" + getRandomInt(1, 10) + ".png";
    }

    private int getRandomInt(int min, int max) {
        return random.nextInt(max + 1 - min) + min;
    }
}