package com.ipin.whatsappclone.entity;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;

import com.ipin.whatsappclone.common.BaseAuditingEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends BaseAuditingEntity {
    
    private static final int LAST_ACTIVE_INTERVAL = 5;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String firstName;
    String lastName;
    String email;
    LocalDateTime lastSeen;
    
    @OneToMany(mappedBy = "sender")
    List<ChatEntity> chatsAsSender;
    
    @OneToMany(mappedBy = "recipient")
    List<ChatEntity> chatsAsRecipient;
    
    @Transient
    boolean isUserOnline() {
        return lastSeen != null && lastSeen.isAfter(LocalDateTime.now().minusMinutes(LAST_ACTIVE_INTERVAL));
    }
}
