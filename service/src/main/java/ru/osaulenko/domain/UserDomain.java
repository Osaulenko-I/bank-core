package ru.osaulenko.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDomain {
    private UUID userId;
    private String name;
    private HairColorDomain hairColor;
    private boolean male;
    private List<UUID> friends = new LinkedList<>();
    private List<UUID> accounts = new LinkedList<>();

    public void addFriends(UUID friend) {
        if (friend == null) {
            throw new IllegalArgumentException("friend must be not null");
        }
        if (userId.equals(friend)) {
            throw new IllegalArgumentException("can't add self as friend");
        }
        if (friends.contains(friend)) {
            throw new IllegalArgumentException("friend has already been added");
        }

        friends.add(friend);
    }

    public void removeFriend(UUID friend) {
        if (friend == null) {
            throw new IllegalArgumentException("friend must be not null");
        }
        if (userId.equals(friend)) {
            throw new IllegalArgumentException("can't remove self as friend");
        }
        if (!friends.contains(friend)) {
            throw new IllegalArgumentException("friend not found");
        }

        friends.remove(friend);
    }

    public boolean isFriend(UUID friend) {
        return friends.contains(friend);
    }

    public void addAccount(UUID account) {
        if (account == null) {
            throw new RuntimeException("account must be not null");
        }
        if (accounts.contains(account)) {
            throw new RuntimeException("account has already been added");
        }

        accounts.add(account);
    }
}
