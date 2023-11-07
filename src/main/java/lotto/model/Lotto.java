package lotto.model;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import lotto.exception.Exceptions;
import lotto.view.OutputView;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto() {
        this(Randoms.pickUniqueNumbersInRange(1, 45, 6));
    }

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException();
        }
        Exceptions.checkLottoDuplicate(numbers);
    }

    public int checkNumber(Lotto answerNumber) {
        return (int) numbers.stream().filter(answerNumber.getNumbers()::contains).count();
    }

    public boolean checkBonus(int bonusNumber) {
        return numbers.contains(bonusNumber);
    }

    public void printLotto() {
        OutputView.printMessage(numbers);
    }

    public List<Integer> getNumbers() {
        return numbers;
    }
}
