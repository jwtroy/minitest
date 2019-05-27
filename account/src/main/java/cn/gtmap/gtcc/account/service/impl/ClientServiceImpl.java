package cn.gtmap.gtcc.account.service.impl;

import cn.gtmap.gtcc.account.dao.ClientRepo;
import cn.gtmap.gtcc.account.service.ClientService;
import cn.gtmap.gtcc.domain.sec.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * .ClientServiceImpl
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/25 10:27
 */
@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "clients")
public class ClientServiceImpl implements ClientService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ClientRepo clientRepo;


    @Override
    @Cacheable(key = "#clientId")
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return new CD(getClientByClientId(clientId));
    }

    /**
     * get client by id
     *
     * @param id
     * @return
     */
    @Override
    public Client getClient(String id) {
        return clientRepo.findOne(id);
    }

    /**
     * get client by client id
     *
     * @param clientId
     * @return
     */
    @Override
    public Client getClientByClientId(String clientId) throws ClientRegistrationException {
        Optional<Client> client = clientRepo.findByClientId(clientId);
        if (!client.isPresent()) throw new ClientRegistrationException("client id [" + clientId + "] not found");
        return client.get();
    }

    /**
     * get clients
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<Client> getClients(Pageable pageable) {
        return clientRepo.findAll(pageable);
    }

    /**
     * save client
     *
     * @param client
     * @return
     */
    @Override
    @Transactional
    @CachePut(key = "#client.clientId")
    public Client saveClient(Client client) {
        return clientRepo.save(client);
    }

    /**
     * save clients
     *
     * @param clients
     * @return
     */
    @Override
    @Transactional
    public Iterable<Client> saveClients(Iterable<Client> clients) {
        return clientRepo.save(clients);
    }

    /**
     * delete client
     *
     * @param client
     */
    @Override
    @Transactional
    @CacheEvict(key = "#client.clientId")
    public void deleteClient(Client client) {
        clientRepo.delete(client);
    }

    /**
     * delete clinet by id
     *
     * @param id
     */
    @Override
    @Transactional
    @CacheEvict(key = "#client.clientId")
    public void deleteClient(String id) {
        clientRepo.delete(id);
    }


    /**
     * .ClientDetails
     */
    public static class CD implements ClientDetails {

        private Client client;

        public CD(Client client) {
            Assert.notNull(client, "client can't be null");
            this.client = client;
        }

        /**
         * The client id.
         *
         * @return The client id.
         */
        @Override
        public String getClientId() {
            return client.getClientId();
        }

        /**
         * The resources that this client can access. Can be ignored by callers if empty.
         *
         * @return The resources of this client.
         */
        @Override
        public Set<String> getResourceIds() {
            return StringUtils.commaDelimitedListToSet(client.getResourceIds());
        }

        /**
         * Whether a secret is required to authenticate this client.
         *
         * @return Whether a secret is required to authenticate this client.
         */
        @Override
        public boolean isSecretRequired() {
            return client.getClientSecret() != null;
        }

        /**
         * The client secret. Ignored if the {@link #isSecretRequired() secret isn't required}.
         *
         * @return The client secret.
         */
        @Override
        public String getClientSecret() {
            return client.getClientSecret();
        }

        /**
         * Whether this client is limited to a specific scope. If false, the scope of the authentication request will be
         * ignored.
         *
         * @return Whether this client is limited to a specific scope.
         */
        @Override
        public boolean isScoped() {
            return !StringUtils.isEmpty(client.getScopes());
        }

        /**
         * The scope of this client. Empty if the client isn't scoped.
         *
         * @return The scope of this client.
         */
        @Override
        public Set<String> getScope() {
            return StringUtils.commaDelimitedListToSet(client.getScopes());
        }

        /**
         * The grant types for which this client is authorized.
         *
         * @return The grant types for which this client is authorized.
         */
        @Override
        public Set<String> getAuthorizedGrantTypes() {
            return StringUtils.commaDelimitedListToSet(client.getAuthorizedGrantTypes());
        }

        /**
         * The pre-defined redirect URI for this client to use during the "authorization_code" access grant. See OAuth spec,
         * section 4.1.1.
         *
         * @return The pre-defined redirect URI for this client.
         */
        @Override
        public Set<String> getRegisteredRedirectUri() {
            return StringUtils.commaDelimitedListToSet(client.getRegisteredRedirectUris());
        }

        /**
         * Returns the authorities that are granted to the OAuth client. Cannot return <code>null</code>.
         * Note that these are NOT the authorities that are granted to the user with an authorized access token.
         * Instead, these authorities are inherent to the client itself.
         *
         * @return the authorities (never <code>null</code>)
         */
        @Override
        public Collection<GrantedAuthority> getAuthorities() {
            Set<GrantedAuthority> authorities = new LinkedHashSet<>();
            String[] array = StringUtils.commaDelimitedListToStringArray(client.getAuthorities());
            for (String a : array) {
                authorities.add(new SimpleGrantedAuthority(a));
            }
            return authorities;
        }

        /**
         * The access token validity period for this client. Null if not set explicitly (implementations might use that fact
         * to provide a default value for instance).
         *
         * @return the access token validity period
         */
        @Override
        public Integer getAccessTokenValiditySeconds() {
            return client.getAccessTokenValiditySeconds();
        }

        /**
         * The refresh token validity period for this client. Null for default value set by token service, and
         * zero or negative for non-expiring tokens.
         *
         * @return the refresh token validity period
         */
        @Override
        public Integer getRefreshTokenValiditySeconds() {
            return client.getRefreshTokenValiditySeconds();
        }

        public Set<String> getAutoApproveScope() {
            return StringUtils.commaDelimitedListToSet(client.getAutoApproveScopes());
        }

        /**
         * Test whether client needs user approval for a particular scope.
         *
         * @param scope the scope to consider
         * @return true if this client does not need user approval
         */
        @Override
        public boolean isAutoApprove(String scope) {
            if (client.getAutoApproveScopes() == null) return false;
            for (String auto : getAutoApproveScope()) {
                if (auto.equals("true") || auto.equals("*") || scope.matches(auto)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Additional information for this client, not needed by the vanilla OAuth protocol but might be useful, for example,
         * for storing descriptive information.
         *
         * @return a map of additional information
         */
        @Override
        public Map<String, Object> getAdditionalInformation() {
            try {
                return (Map<String, Object>) objectMapper.readValue(client.getAdditionalInfo(), Map.class);
            } catch (IOException e) {
                return new HashMap<>();
            }
        }
    }
}
