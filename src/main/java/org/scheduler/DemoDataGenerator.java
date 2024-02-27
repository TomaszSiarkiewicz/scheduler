package org.scheduler;

import lombok.RequiredArgsConstructor;
import org.scheduler.entities.*;
import org.scheduler.enums.AvailabilityType;
import org.scheduler.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

import static java.time.DayOfWeek.*;

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


    private final Integer SHIFT_LENGTH = 11;
    private final Integer SHIFT_START_TIME = 11;
    private final LocalDate START_DATE = LocalDate.now().withDayOfMonth(1);
    private final int INITIAL_ROSTER_LENGTH_IN_DAYS = START_DATE.lengthOfMonth();
    private final Long SINGLETON_SCHEDULE_ID = 1L;
    private final int NUMBER_OF_EMPLOYEES = 20;
    private final int NUMBER_OF_LOCATIONS = 5;
    private final int NUMBER_OF_SKILLS = 3;

    private Random random = new Random();
    private List<Employee> employees;
    private List<Skill> allSkills;
    private List<Location> allLocation;
    private List<Availability> allAvailabilities;

    @Override
    public void run(String... args) {
        logger.info("Generating demo data");
        createSkills();
        allSkills = skillRepository.findAll();
        createLocations();
        allLocation = locationRepository.findAll();
        createEmployees();
        employees = employeeRepository.findAll();
        createAvailabilities();
        allAvailabilities = availabilityRepository.findAll();
        createShiftGenerationRules();


    }

    private void createShiftGenerationRules() {
        allLocation.forEach(location -> {
            allSkills.forEach(skill -> {
                generateRule(location,skill);
            });
        });
    }


    private void createAvailabilities() {
        employees.forEach(this::generateAvailabilitiesForEmployee);
    }

    private void createSkills() {
        List<Skill> skills = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_SKILLS; i++) {
            skills.add(new Skill("Skill " + (i + 1)));
        }
        skillRepository.saveAll(skills);
    }

    private void createLocations() {
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_LOCATIONS; i++) {
            locations.add(new Location("Location " + (i + 1)));
        }
        locationRepository.saveAll(locations);
    }

    private void createEmployees() {
        List<Employee> employees = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_EMPLOYEES; i++) {
            employees.add(new Employee("Employee " + (i + 1), random.nextInt(5) + 10, random.nextInt(5) + 15, getSkillSet(allSkills), getLocations(allLocation)));
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

    private void generateAvailabilitiesForEmployee(Employee employee) {
        int daysRequested = random.nextInt(10);
        Set<Integer> days = new HashSet<>();

        for (int i = 0; i < daysRequested; i++) {
            days.add(random.nextInt(INITIAL_ROSTER_LENGTH_IN_DAYS) + 1);  // day of month to generate availability
        }
        days.forEach(integer -> generateAvailabilityForGivenDay(integer, employee));
    }

    private void generateAvailabilityForGivenDay(Integer integer, Employee employee) {
        availabilityRepository.save(new Availability(employee, START_DATE.withDayOfMonth(integer), AvailabilityType.UNAVAILABLE));
    }

    private void generateRule(Location location, Skill skill) {
        shiftGenerateRuleRepository.save(new ShiftGenerateRule(location,skill, List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY), SHIFT_START_TIME,(SHIFT_START_TIME + SHIFT_LENGTH)));
    }
}
