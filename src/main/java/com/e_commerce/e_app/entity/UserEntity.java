package com.e_commerce.e_app.entity;

import com.e_commerce.e_app.util.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.jilt.Builder;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user")
@Builder
public class UserEntity extends AbstractAuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userSequenceGenerator")
    @SequenceGenerator(name = "userSequenceGenerator",sequenceName = "user_sequence",allocationSize=1)
    @Column(name = "id")
    private Long id;
    private String lastName;
    private String firstName;
    private String email;
    private String imageUrl;
    private UUID publicId;
    private String addressStreet;
    private String addressCity;
    private String addressZipCode;
    private String addressCountry;
    private Instant lastSeen;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name ="user_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name",referencedColumnName = "name")}
    )
    private Set<AuthorityEntity> authorities = new HashSet<>();

    public UserEntity() {
    }

    public UserEntity(Long id, String lastName, String firstName,
                      String email, String imageUrl, UUID publicId,
                      String addressStreet, String addressCity,
                      String addressZipCode, String addressCountry, Instant lastSeen, Set<AuthorityEntity> authorities) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.imageUrl = imageUrl;
        this.publicId = publicId;
        this.addressStreet = addressStreet;
        this.addressCity = addressCity;
        this.addressZipCode = addressZipCode;
        this.addressCountry = addressCountry;
        this.lastSeen = lastSeen;
        this.authorities = authorities;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressZipCode() {
        return addressZipCode;
    }

    public void setAddressZipCode(String addressZipCode) {
        this.addressZipCode = addressZipCode;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public Instant getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Instant lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Set<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }
}
