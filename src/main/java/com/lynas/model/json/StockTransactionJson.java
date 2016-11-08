package com.lynas.model.json;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by LynAs on 10-Apr-16
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public
@Data
class StockTransactionJson implements Serializable {
    long stockTransactionId;
    long stockId;
    long bookId;
    long organizationId;
}
