package com.lynas.model.util;

import com.lynas.model.AccountTransaction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by LynAs on 22-Mar-16
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public @Data class CompositeAccountTransaction {
    private List<AccountTransaction> accountTransactions;
    private double debitTotal;
    private double creditTotal;

}
