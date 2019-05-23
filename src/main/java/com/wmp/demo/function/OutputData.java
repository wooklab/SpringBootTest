package com.wmp.demo.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutputData {
    private static final Logger log = LoggerFactory.getLogger(OutputData.class);
    private final String originData;
    private final Integer outputUnit;
    private StringBuilder quotientValue;
    private StringBuilder remainderValue;

    public String getQuotientValue() {
        return this.quotientValue.toString();
    }

    public String getRemainderValue() {
        return this.remainderValue.toString();
    }

    public OutputData(String originData, Integer outputUnit) {
        this.originData = originData;
        this.outputUnit = outputUnit;
        this.quotientValue = new StringBuilder();
        this.remainderValue = new StringBuilder();
    }

    public void doProcess() {
        int quotientCount = this.originData.length() / this.outputUnit;
        boolean existQuotient = quotientCount >= 1;
        if (!existQuotient) {
            this.remainderValue.append(this.originData);
            return;
        }

        setValuePretty(quotientCount);

    }

    private void setValue(int quotientCount) {
        this.quotientValue.append(this.originData.substring(0, (quotientCount * this.outputUnit) - 1));
        this.remainderValue.append(this.originData.substring(quotientCount * this.outputUnit, this.originData.length()));
    }

    private void setValuePretty(int quotientCount) {
        log.debug("set data pretty");
        for (int idx = 0; idx < quotientCount; idx++) {
            this.quotientValue
                    .append(this.originData.substring(idx * outputUnit, ((idx + 1) * outputUnit) - 1))
                    .append("\r\n");
        }
        this.remainderValue.append(this.originData.substring(quotientCount * this.outputUnit, this.originData.length()));
    }

}
