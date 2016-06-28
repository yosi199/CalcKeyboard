package input.calculator;

import java.util.ArrayList;

/**
 * Created by yosimizrachi on 28/06/2016.
 */
public class CalcManager {

    private static final CalcManager INSTANCE = new CalcManager();

    private ArrayList<CalculationItem> mCalculationItems;

    private CalcManager() {
    }

    public static CalcManager getInstance() {
        return INSTANCE;
    }


    public void addToHistory(CalculationItem calculationItem) {
        if (mCalculationItems == null) {
            mCalculationItems = new ArrayList<>();
        }
        mCalculationItems.add(calculationItem);
    }

    public ArrayList<CalculationItem> getHistoryItems() {
        return mCalculationItems;
    }

    /**
     * Will parse user input and calculate the result
     *
     * @param input the user input
     * @return the result of calculation
     */
    public int calculate(String input) {
        CalculationItem calculationItem = new CalculationItem();
        getOperationType(input, calculationItem);
        parseMemberA(input, calculationItem);
        parseMemberB(input, calculationItem);
        addToHistory(calculationItem);

        return calculationItem.calculateValue();
    }

    private final void getOperationType(String input, CalculationItem item) {

        for (int i = 0; i < input.length(); i++) {
            char currChar = input.charAt(i);
            switch (currChar) {
                case '-':
                    item.setType(CalculationItem.OperationType.SUBTRACT);
                    item.getType().setOperatorIndex(i);
                    break;
                case '+':
                    item.setType(CalculationItem.OperationType.ADD);
                    item.getType().setOperatorIndex(i);
                    break;
                case '*':
                    item.setType(CalculationItem.OperationType.MULTIPLY);
                    item.getType().setOperatorIndex(i);
                    break;
                case '/':
                    item.setType(CalculationItem.OperationType.DIVIDE);
                    item.getType().setOperatorIndex(i);
                    break;
            }
        }
    }

    private final void parseMemberA(String input, CalculationItem item) {

        if (item.getType() != null) {
            int operatorIndex = item.getType().getOperatorIndex();
            item.setMemberA(Integer.valueOf(input.substring(0, operatorIndex)));
        }
    }

    private final void parseMemberB(String input, CalculationItem item) {

        if (item.getType() != null) {
            int operatorIndex = item.getType().getOperatorIndex();
            item.setMemberB(Integer.valueOf(input.substring(operatorIndex + 1, input.length())));
        }
    }


}
