package org.acme.employeescheduling.rest;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.enterprise.context.ApplicationScoped;

import org.acme.employeescheduling.domain.Employee;
import org.acme.employeescheduling.domain.EmployeeSchedule;
import org.acme.employeescheduling.domain.Shift;

@ApplicationScoped
public class DemoDataGenerator {

    public enum DemoData {
        SMALL,
        LARGE
    }

    private static final String[] FIRST_NAMES = { "Amy", "Beth", "Chad", "Dan", "Elsa", "Flo", "Gus", "Hugo", "Ivy", "Jay" };
    private static final String[] LAST_NAMES = { "Cole", "Fox", "Green", "Jones", "King", "Li", "Poe", "Rye", "Smith", "Watt" };
    private static final String[] REQUIRED_SKILLS = { "Doctor", "Nurse" };
    private static final String[] OPTIONAL_SKILLS = { "Anaesthetics", "Cardiology" };
    private static final String[] LOCATIONS = { "Ambulatory care", "Critical care", "Pediatric care" };
    private static final Duration SHIFT_LENGTH = Duration.ofHours(8);
    private static final LocalTime MORNING_SHIFT_START_TIME = LocalTime.of(6, 0);
    private static final LocalTime DAY_SHIFT_START_TIME = LocalTime.of(9, 0);
    private static final LocalTime AFTERNOON_SHIFT_START_TIME = LocalTime.of(14, 0);
    private static final LocalTime NIGHT_SHIFT_START_TIME = LocalTime.of(22, 0);

    static final LocalTime[][] SHIFT_START_TIMES_COMBOS = {
            { MORNING_SHIFT_START_TIME, AFTERNOON_SHIFT_START_TIME },
            { MORNING_SHIFT_START_TIME, AFTERNOON_SHIFT_START_TIME, NIGHT_SHIFT_START_TIME },
            { MORNING_SHIFT_START_TIME, DAY_SHIFT_START_TIME, AFTERNOON_SHIFT_START_TIME, NIGHT_SHIFT_START_TIME },
    };

    Map<String, List<LocalTime>> locationToShiftStartTimeListMap = new HashMap<>();

    public EmployeeSchedule generateDemoData() {
        EmployeeSchedule employeeSchedule = new EmployeeSchedule();

        int initialRosterLengthInDays = 14;
        LocalDate startDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));

        Random random = new Random(0);

        int shiftTemplateIndex = 0;
        for (String location : LOCATIONS) {
            locationToShiftStartTimeListMap.put(location, List.of(SHIFT_START_TIMES_COMBOS[shiftTemplateIndex]));
            shiftTemplateIndex = (shiftTemplateIndex + 1) % SHIFT_START_TIMES_COMBOS.length;
        }

        List<String> namePermutations = joinAllCombinations(FIRST_NAMES, LAST_NAMES);
        Collections.shuffle(namePermutations, random);

        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Set<String> skills = pickSubset(List.of(OPTIONAL_SKILLS), random, 3, 1);
            skills.add(pickRandom(REQUIRED_SKILLS, random));
            Employee employee = new Employee(namePermutations.get(i), skills, new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>());
            employees.add(employee);
        }
        employeeSchedule.setEmployees(employees);

        List<Shift> shifts = new LinkedList<>();
        for (int i = 0; i < initialRosterLengthInDays; i++) {
            Set<Employee> employeesWithAvailabilitiesOnDay = pickSubset(employees, random, 4, 3, 2, 1);
            LocalDate date = startDate.plusDays(i);
            for (Employee employee : employeesWithAvailabilitiesOnDay) {
                switch (random.nextInt(3)) {
                    case 0 -> employee.getUnavailableDates().add(date);
                    case 1 -> employee.getUndesiredDates().add(date);
                    case 2 -> employee.getDesiredDates().add(date);
                }
            }
            shifts.addAll(generateShiftsForDay(date, random));
        }
        AtomicInteger countShift = new AtomicInteger();
        shifts.forEach(s -> s.setId(Integer.toString(countShift.getAndIncrement())));
        employeeSchedule.setShifts(shifts);

        return employeeSchedule;
    }

    private List<Shift> generateShiftsForDay(LocalDate date, Random random) {
        List<Shift> shifts = new LinkedList<>();
        for (String location : LOCATIONS) {
            List<LocalTime> shiftStartTimes = locationToShiftStartTimeListMap.get(location);
            for (LocalTime shiftStartTime : shiftStartTimes) {
                LocalDateTime shiftStartDateTime = date.atTime(shiftStartTime);
                LocalDateTime shiftEndDateTime = shiftStartDateTime.plus(SHIFT_LENGTH);
                shifts.addAll(generateShiftForTimeslot(shiftStartDateTime, shiftEndDateTime, location, random));
            }
        }
        return shifts;
    }

    private List<Shift> generateShiftForTimeslot(LocalDateTime timeslotStart, LocalDateTime timeslotEnd, String location,
            Random random) {
        int shiftCount = 1;

        if (random.nextDouble() > 0.9) {
            // generate an extra shift
            shiftCount++;
        }

        List<Shift> shifts = new LinkedList<>();
        for (int i = 0; i < shiftCount; i++) {
            String requiredSkill;
            if (random.nextBoolean()) {
                requiredSkill = pickRandom(REQUIRED_SKILLS, random);
            } else {
                requiredSkill = pickRandom(OPTIONAL_SKILLS, random);
            }
            shifts.add(new Shift(timeslotStart, timeslotEnd, location, requiredSkill));
        }
        return shifts;
    }

    private <T> T pickRandom(T[] source, Random random) {
        return source[random.nextInt(source.length)];
    }

    private <T> Set<T> pickSubset(List<T> sourceSet, Random random, int... distribution) {
        int probabilitySum = 0;
        for (int probability : distribution) {
            probabilitySum += probability;
        }
        int choice = random.nextInt(probabilitySum);
        int numOfItems = 0;
        while (choice >= distribution[numOfItems]) {
            choice -= distribution[numOfItems];
            numOfItems++;
        }
        List<T> items = new ArrayList<>(sourceSet);
        Collections.shuffle(items, random);
        return new HashSet<>(items.subList(0, numOfItems + 1));
    }

    private List<String> joinAllCombinations(String[]... partArrays) {
        int size = 1;
        for (String[] partArray : partArrays) {
            size *= partArray.length;
        }
        List<String> out = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            StringBuilder item = new StringBuilder();
            int sizePerIncrement = 1;
            for (String[] partArray : partArrays) {
                item.append(' ');
                item.append(partArray[(i / sizePerIncrement) % partArray.length]);
                sizePerIncrement *= partArray.length;
            }
            item.delete(0, 1);
            out.add(item.toString());
        }
        return out;
    }
}
