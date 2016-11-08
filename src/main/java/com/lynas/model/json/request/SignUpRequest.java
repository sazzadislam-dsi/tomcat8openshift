package com.lynas.model.json.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by LynAs on 10-May-16
 */

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public
@Data
class SignUpRequest implements Serializable {
    private String organizationName;
    private String userName;
    private String password;
}
