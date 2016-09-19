package com.lvtulife.system.component.oauth2;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/4/19 0019.
 */
@Component(value = "oauthRepository")
public class OauthRepositoryJdbc {


    private static OauthClientDetailsRowMapper oauthClientDetailsRowMapper = new OauthClientDetailsRowMapper();


    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;


    public OauthClientDetails findOauthClientDetails(String clientId) {
        final String sql = " select * from oauth_client_details where  client_id = ? ";
        final List<OauthClientDetails> list = this.jdbcTemplate.query(sql, new Object[]{clientId}, oauthClientDetailsRowMapper);
        return list.isEmpty() ? null : list.get(0);
    }


    public List<OauthClientDetails> findAllOauthClientDetails() {
        final String sql = " select * from oauth_client_details order by create_time desc ";
        return this.jdbcTemplate.query(sql, oauthClientDetailsRowMapper);
    }


    public void updateOauthClientDetailsArchive(String clientId, boolean archive) {
        final String sql = " update oauth_client_details set archived = ? where client_id = ? ";
        this.jdbcTemplate.update(sql, archive, clientId);
    }


    public void saveOauthClientDetails(final OauthClientDetails clientDetails) {
        final String sql = " insert into oauth_client_details(client_id,resource_ids,client_secret,scope,authorized_grant_types,web_server_redirect_uri," +
                " authorities,access_token_validity,refresh_token_validity,additional_information,trusted,autoapprove) values (?,?,?,?,?,?,?,?,?,?,?,?)";

        this.jdbcTemplate.update(sql, ps -> {
            ps.setString(1, clientDetails.clientId());
            ps.setString(2, clientDetails.resourceIds());

            ps.setString(3, clientDetails.clientSecret());
            ps.setString(4, clientDetails.scope());

            ps.setString(5, clientDetails.authorizedGrantTypes());
            ps.setString(6, clientDetails.webServerRedirectUri());

            ps.setString(7, clientDetails.authorities());
            ps.setObject(8, clientDetails.accessTokenValidity());

            ps.setObject(9, clientDetails.refreshTokenValidity());
            ps.setString(10, clientDetails.additionalInformation());

            ps.setBoolean(11, clientDetails.trusted());
            ps.setString(12, clientDetails.autoApprove());

        });
    }
}
