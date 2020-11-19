package io.fysus.elo;

import static java.lang.System.arraycopy;
import static java.lang.System.err;
import static java.lang.System.exit;

import io.fysus.elo.controller.Controller;
import io.fysus.elo.error.EloException;
import java.util.Arrays;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class CommandRunner implements ApplicationRunner {

    private Map<String, Controller> controllers;


    @Override
    public void run(ApplicationArguments arguments) {

        String[] args = new String[arguments.getNonOptionArgs().size()];

        args = arguments.getNonOptionArgs().toArray(args);

        log.debug("EXECUTING : command line runner with args {}", Arrays.toString(args));

        try {

            validateNumberOrParameters(args);

            MainMenuOption option = getMainMenuOption(args);

            Controller controller = controllers.get(option.getControllerName());

            String[] subarray = getParamsForTheNextStep(args);

            controller.execute(subarray);

        } catch (EloException e){
            err.println(e.getMessage());
            exit(1);
        }
    }

    private String[] getParamsForTheNextStep(String[] args) {
        String[] subarray = new String[args.length - 1];

        arraycopy(args, 1, subarray, 0, subarray.length);
        return subarray;
    }

    private void validateNumberOrParameters(String[] args) {
        if (args.length < 1) {
            throw new EloException("Requires at least 1 parameter. Possible options "
                + Arrays.toString(MainMenuOption.values()));
        }
    }

    private MainMenuOption getMainMenuOption(String[] args) {
        MainMenuOption option;
        try {
            option = MainMenuOption.valueOf(args[0]);
        } catch (IllegalArgumentException e) {
            throw new EloException("Incorrect value for first parameter [" + args[0] + "], possible values are "
                + Arrays.toString(MainMenuOption.values()));
        }
        return option;
    }

    enum MainMenuOption {
        LIST_PLAYERS("listPlayersController"),
        SHOW_DETAILS("showPlayerDetailsController"),
        GENERATE_MATCH("generateMatches");

        private final String controllerName;

        MainMenuOption(String controllerName) {
            this.controllerName = controllerName;
        }

        public String getControllerName() {
            return controllerName;
        }
    }

}
