package com.nulab.howudonodocker.repository;

import com.nulab.howudonodocker.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {
}
