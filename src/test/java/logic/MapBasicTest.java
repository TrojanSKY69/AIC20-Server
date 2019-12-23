package logic;

import ir.sharif.aichallenge.server.logic.entities.BaseUnit;
import ir.sharif.aichallenge.server.logic.entities.Unit;
import ir.sharif.aichallenge.server.logic.map.Cell;
import ir.sharif.aichallenge.server.logic.map.Map;
import ir.sharif.aichallenge.server.logic.map.Path;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MapBasicTest {

    private static Map map;
    private static HashMap<Integer, Unit> units;

    @BeforeAll
    public static void init() {
        final int size = 10;
        map = new Map(size, size);
        units = new HashMap<>();
        map.addPath(new Path(0, IntStream.range(0, size).mapToObj(i -> new Cell(i, i)).collect(Collectors.toList())));
        map.addPath(new Path(1, IntStream.range(0, size).mapToObj(i -> new Cell(i, 0)).collect(Collectors.toList())));
        map.addPath(new Path(2, IntStream.range(0, size).mapToObj(i -> new Cell(i, size - 1)).collect(Collectors.toList())));
    }

    @Test
    public void testAll() {
        testPutUnit();
        testUnitsInRangePart0();
        testMoveUnitPart0();
        testMoveUnitPart1();
    }

    public void testPutUnit() {
        map.putUnit(addUnit(new Unit(0, BaseUnit.getInstance(0, 0), null)), 0);
        map.putUnit(addUnit(new Unit(1, BaseUnit.getInstance(1, 0), null)), 1);
        map.putUnit(addUnit(new Unit(2, BaseUnit.getInstance(0, 0), null)), 2);

        assertUnitIdsAt(0, 0, 0, 1);
        assertUnitIdsAt(0, 9, 2);
    }

    public void testUnitsInRangePart0() {
        assertUnitIdsInRange(units.get(0), 0, 1);
        assertUnitIdsInRange(units.get(1), 0, 1);
        assertUnitIdsInRange(units.get(2), 2);
    }

    public void testMoveUnitPart0() {
        map.moveUnit(units.get(0), units.get(0).nextCell());
        map.moveUnit(units.get(1), units.get(1).nextCell());
        map.moveUnit(units.get(2), units.get(2).nextCell());

        assertUnitIdsAt(1, 1, 0);
        assertUnitIdsAt(1, 0, 1);
        assertUnitIdsAt(1, 9, 2);
    }

    public void testUnitsInRangePart1() {
        assertUnitIdsInRange(units.get(0), 0, 1);
        assertUnitIdsInRange(units.get(1), 0, 1);
        assertUnitIdsInRange(units.get(2), 2);
    }

    public void testMoveUnitPart1() {
        map.moveUnit(units.get(0), units.get(0).nextCell());
        map.moveUnit(units.get(1), units.get(1).nextCell());
        map.moveUnit(units.get(2), units.get(2).nextCell());

        assertUnitIdsAt(2, 2, 0);
        assertUnitIdsAt(2, 0, 1);
        assertUnitIdsAt(2, 9, 2);

        map.moveUnit(units.get(0), units.get(0).nextCell());
        map.moveUnit(units.get(1), units.get(1).nextCell());
        map.moveUnit(units.get(2), units.get(2).nextCell());

        assertUnitIdsAt(3, 3, 0);
        assertUnitIdsAt(3, 0, 1);
        assertUnitIdsAt(3, 9, 2);
    }

    public void testUnitsInRangePart2() {
        assertUnitIdsInRange(units.get(0), 0);
        assertUnitIdsInRange(units.get(1), 1);
        assertUnitIdsInRange(units.get(2), 2);
    }

    public void assertUnitIdsAt(int row, int col, Integer... ids) {
        assertEquals(new HashSet<>(Arrays.asList(ids)),
                map.getUnits(row, col).map(Unit::getUnitId).collect(Collectors.toSet()));
    }

    public void assertUnitIdsInRange(Unit unit, Integer... ids) {
        assertEquals(new HashSet<>(Arrays.asList(ids)),
                map.getUnitsInManhattanRange(unit.getCell(), unit.getRange()).map(Unit::getUnitId).collect(Collectors.toSet()));
    }

    private Unit addUnit(Unit unit) {
        units.put(unit.getUnitId(), unit);
        return unit;
    }
}