package au.com.nab.icommerce.customer.service.entity;

import javax.persistence.*;

@Entity
@Table(name = "nab_customer")
public class Customer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "token")
    private String token;

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getProvider() {
        return this.provider;
    }

    public String getProviderId() {
        return this.providerId;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public String getToken() {
        return this.token;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
