package lotto.view.input;

import camp.nextstep.edu.missionutils.Console;
import lotto.view.OutputView;
import lotto.view.Prompt;
import lotto.view.Status;

public abstract class InputView {
    protected Status status;

    protected void getInput(Prompt message) {
        status = Status.GOT_NOTHING;
        while (status.getStatus()) {
            getInputStatus(message);
        }
    }

    protected void getInputStatus(Prompt message) {
        try {
            OutputView.printMessage(message);
            Validation(Console.readLine());
            status = Status.GOT_MESSAGE;
        } catch (IllegalArgumentException e) {
            OutputView.printMessage(e.getMessage());
            status = Status.GOT_ERROR;
        }
    }

    protected abstract void Validation(String s);
}