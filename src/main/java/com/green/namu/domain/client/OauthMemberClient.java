package com.green.namu.domain.client;

import com.green.namu.domain.OauthMember;
import com.green.namu.domain.OauthServerType;

public interface OauthMemberClient {

    OauthServerType supportServer();

    OauthMember fetch(String code); // Auth code

}