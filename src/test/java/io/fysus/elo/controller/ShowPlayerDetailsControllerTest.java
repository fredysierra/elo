package io.fysus.elo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import io.fysus.elo.domain.DetailPlayerInformation;
import io.fysus.elo.error.EloException;
import io.fysus.elo.formatter.OutputFormatter;
import io.fysus.elo.service.DetailPlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShowPlayerDetailsControllerTest {

    @InjectMocks
    ShowPlayerDetailsController controller;

    @Mock
    DetailPlayerService detailPlayerService;

    @Mock
    OutputFormatter<DetailPlayerInformation> formatter;

    @Test
    void testExecuteWithoutPlayerIdOption() {
        Exception exception = assertThrows(EloException.class, () -> controller.execute(new String[]{}));
        assertEquals("PlayerId is required for this option", exception.getMessage());
    }

    @Test
    void testExecuteWithMoreThanOneParam() {
        Exception exception = assertThrows(EloException.class, () -> controller.execute(new String[]{"PLAYER_ID", "ANOTHER_OPTION"}));
        assertEquals("Extra parameters no required [PLAYER_ID, ANOTHER_OPTION]. Just playerId is required", exception.getMessage());
    }

    @Test
    void testExecute() {
        controller.execute(new String[]{"1"});
        verify(detailPlayerService).getDetailPlayerReport(eq("1"));
        verify(formatter).format(any());
    }
}