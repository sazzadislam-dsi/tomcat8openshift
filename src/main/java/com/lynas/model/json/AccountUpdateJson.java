package com.lynas.model.json;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LynAs on 10-Apr-16
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public @Data class AccountUpdateJson {
    long accountId;
    String accountNewName;
    long bookId;
    long organizationId;
}
