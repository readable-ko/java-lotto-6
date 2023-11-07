package lotto.controller;

import java.util.HashMap;
import java.util.List;
import lotto.model.Lotto;
import lotto.model.Lottos;
import lotto.view.OutputView;
import lotto.view.Status;
import lotto.view.input.AmountInputView;
import lotto.view.input.BonusInputView;
import lotto.view.input.LottoInputView;

public class LottoController {
    private static final String GATHERING = "당첨 통계\n---";
    private static final String STATISTIC_MESSAGE = "%d개 일치 (%s) - %d개\n";
    private static final String INCOME_MESSAGE = "총 수익률은 %d%%입니다.";
    private final Lottos lottos = new Lottos();
    private Lotto answerNumber;
    private long totalEarn = 0;
    private Status status = Status.GOT_NOTHING;

    private void createLotto() {
        try {
            List<Integer> answerNumbers = new LottoInputView().getLottoNumbers();
            answerNumber = new Lotto(answerNumbers);
            status = Status.GOT_MESSAGE;
        } catch (IllegalArgumentException e) {
            OutputView.printMessage(e.getMessage());
            status = Status.GOT_ERROR;
        }
    }

    private void getAnswerNumbers() {
        while (status.getStatus()) {
            createLotto();
        }
    }

    private void getTotalEarn(long earn) {
        totalEarn += earn;
    }

    private void checkWinTheLottery(int bonusNumber) {
        final HashMap<Rank, Long> rank = lottos.checkWin(answerNumber, bonusNumber);

        OutputView.printMessage(GATHERING);

        rank.forEach((value, wonTimes) -> {
            if (value != Rank.NONE) {
                String message = String.format(STATISTIC_MESSAGE, value.getScore(), value.getEarn(), wonTimes);
                OutputView.printMessage(message);
                getTotalEarn(value.getPrice());
            }
        });
    }

    public void play() {
        long amount = new AmountInputView().getNumberOfLotto();
        lottos.purchaseLotto(amount);
        getAnswerNumbers();

        int bonusNumber = new BonusInputView().getBonusNumber();
        checkWinTheLottery(bonusNumber);

        totalEarn = totalEarn * 100 / amount;
        OutputView.printMessage(String.format(INCOME_MESSAGE, totalEarn));
    }
}
