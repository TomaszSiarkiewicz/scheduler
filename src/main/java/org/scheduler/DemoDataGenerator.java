package org.scheduler;

import lombok.RequiredArgsConstructor;
import org.scheduler.entities.Employee;
import org.scheduler.entities.Location;
import org.scheduler.entities.Skill;
import org.scheduler.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@RequiredArgsConstructor
@Component
public class DemoDataGenerator implements CommandLineRunner {
    private final EmployeeRepository employeeRepository;
    private final SkillRepository skillRepository;
    private final LocationRepository locationRepository;
    private final AvailabilityRepository availabilityRepository;
    private final ShiftRepository shiftRepository;
    private final ShiftGenerateRuleRepository shiftGenerateRuleRepository;
    private final Logger logger = LoggerFactory.getLogger(DemoDataGenerator.class);


    private final Duration SHIFT_LENGTH = Duration.ofHours(11);
    private final LocalTime SHIFT_START_TIME = LocalTime.of(11, 0);
    private final LocalDate START_DATE = LocalDate.now();
    private final int INITIAL_ROSTER_LENGTH_IN_DAYS = START_DATE.lengthOfMonth();
    private final Long SINGLETON_SCHEDULE_ID = 1L;
    private final int NUMBER_OF_EMPLOYEES =20;
    private final int NUMBER_OF_LOCATIONS = 5;
    private final int NUMBER_OF_SKILLS = 3;

    private Random random = new Random();

//todo finish demo data
    @Override
    public void run(String... args) {
        logger.info("Generating demo data");
        createSkills();
        createLocations();
        createEmployees();
    }
    private void createSkills() {
        List<Skill> skills = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_SKILLS; i++) {
            skills.add(new Skill("Skill " + (i +1)));
        }
        skillRepository.saveAll(skills);
    }
    private void createLocations() {
        List<Location> locations= new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_LOCATIONS; i++) {
            locations.add(new Location("Location " + (i + 1)));
        }
        locationRepository.saveAll(locations);
    }
    void createEmployees() {
        List<Skill> allSkills = skillRepository.findAll();
        List<Location> allLocation = locationRepository.findAll();
        List<Employee> employees = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_EMPLOYEES; i++) {
            employees.add(new Employee("Employee " + (i + 1),random.nextInt(5)+10,random.nextInt(5)+15, getSkillSet(allSkills), getLocations(allLocation)));
        }
        employeeRepository.saveAll(employees);
    }

    private Map<Location, Integer> getLocations(List<Location> allLocation) {
        Map<Location, Integer> locations = new HashMap<>();
        allLocation.forEach(location -> {
            locations.put(location, random.nextInt(6));
        });
        return locations;
    }

    private Set<Skill> getSkillSet(List<Skill> allSkills) {
        Collections.shuffle(allSkills);
        Set<Skill> skills = new HashSet<>();
        int skillAmount = random.nextInt(allSkills.size());
        for (int i = 0; i < skillAmount + 1; i++) {
            skills.add(allSkills.get(i));
        }
        return skills;
    }
}
