package com.github.spring.components.transaction.custom.annotation;

/**
 * @author: JianLei
 * @date: 2020/11/8 11:58 上午
 * @description: TransactionAttributes
 */

public class EasyRuleBaseTransactionAttributes {
    private Integer propagate;
    private Class<? extends Throwable>[] rollbacks;

    public Integer getPropagate() {
        return propagate;
    }

    public void setPropagate(Integer propagate) {
        this.propagate = propagate;
    }

    public Class<? extends Throwable>[] getRollbacks() {
        return rollbacks;
    }

    public void setRollbacks(Class<? extends Throwable>[] rollbacks) {
        this.rollbacks = rollbacks;
    }


    public static final class EasyRuleBaseTransactionAttributesBuilder {
        private Integer propagate;
        private Class<? extends Throwable>[] rollbacks;

        private EasyRuleBaseTransactionAttributesBuilder() {
        }

        public static EasyRuleBaseTransactionAttributesBuilder anEasyRuleBaseTransactionAttributes() {
            return new EasyRuleBaseTransactionAttributesBuilder();
        }

        public EasyRuleBaseTransactionAttributesBuilder withPropagate(Integer propagate) {
            this.propagate = propagate;
            return this;
        }

        public EasyRuleBaseTransactionAttributesBuilder withRollbacks(Class<? extends Throwable>[] rollbacks) {
            this.rollbacks = rollbacks;
            return this;
        }

        public EasyRuleBaseTransactionAttributes build() {
            EasyRuleBaseTransactionAttributes easyRuleBaseTransactionAttributes = new EasyRuleBaseTransactionAttributes();
            easyRuleBaseTransactionAttributes.setPropagate(propagate);
            easyRuleBaseTransactionAttributes.setRollbacks(rollbacks);
            return easyRuleBaseTransactionAttributes;
        }
    }
}
