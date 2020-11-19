package io.fysus.elo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import io.fysus.elo.domain.GeneralPlayerInformation;
import io.fysus.elo.error.EloException;
import io.fysus.elo.formatter.OutputFormatter;
import io.fysus.elo.service.GeneralPlayerListService;
import io.fysus.elo.service.GeneralPlayerListService.SortBy;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ListPlayersControllerTest {

    @InjectMocks
    ListPlayersController controller;

    @Mock
    GeneralPlayerListService service;

    @Mock
    OutputFormatter<List<GeneralPlayerInformation>> formatter;


    @Test
    void testExecuteWithIncorrectSortParam() {
        Exception exception = assertThrows(EloException.class, () -> controller.execute(new String[]{"NO_VALID_OPTION"}));
        assertEquals("No valid option, valid options are [ID, NAME, RANKING, WINS, LOSSES]", exception.getMessage());
    }

    @Test
    void testExecuteWithIncorrectSortDirectionParam() {
        Exception exception = assertThrows(EloException.class, () -> controller.execute(new String[]{"RANKING","NO_VALID_DIRECTION_OPTION"}));
        assertEquals("No valid option, valid options are [ASC, DES]", exception.getMessage());
    }

    @Test
    void testExecuteWithoutParamsShouldBeRankingByDefaultWithAscDirection() {
        controller.execute(new String[]{});
        verify(service).getReport(eq(SortBy.RANKING), eq(false));
        verify(formatter).format(anyList());
    }

    @Test
    void testExecuteSortByNameAndDescSortDirection() {
        controller.execute(new String[]{"NAME","DES"});
        verify(service).getReport(eq(SortBy.NAME), eq(true));
        verify(formatter).format(anyList());
    }

    @Test
    void testExecuteSortByNameAndAscSortDirection() {
        controller.execute(new String[]{"NAME","ASC"});
        verify(service).getReport(eq(SortBy.NAME), eq(false));
        verify(formatter).format(anyList());
    }

    @Test
    void testExecuteSortByNameDefaultDirectionASC() {
        controller.execute(new String[]{"NAME"});
        verify(service).getReport(eq(SortBy.NAME), eq(false));
        verify(formatter).format(anyList());
    }
}