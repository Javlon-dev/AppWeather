package com.company.repository.custom;

import com.company.dto.request.ProfileFilterDTO;
import com.company.mapper.ProfileInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProfileCustomRepository {

    private final EntityManager entityManager;

    public List<ProfileInfoMapper> filter(ProfileFilterDTO dto) {
        StringBuilder sql = new StringBuilder(
                "select new com.company.mapper.ProfileInfoMapper(p.id, p.name, p.surname, p.email, p.status, p.role, " +
                        "p.createdDate, p.updatedDate, p.deletedDate) " +
                        "from ProfileEntity p ");

        Map<String, Object> params = new HashMap<>();

        if (Optional.ofNullable(dto.getDeletedDate()).isPresent()) {
            if (dto.getDeletedDate()) {
                sql.append("where p.deletedDate is null ");
            } else {
                sql.append("where p.deletedDate is not null ");
            }
        } else {
            sql.append("where p.deletedDate is not null ");
        }

        if (Optional.ofNullable(dto.getName()).isPresent()) {
            sql.append("and p.name like :name ");
            params.put("name", dto.getName() + "%");
        }

        if (Optional.ofNullable(dto.getSurname()).isPresent()) {
            sql.append("and p.surname like :surname ");
            params.put("surname", dto.getSurname() + "%");
        }

        if (Optional.ofNullable(dto.getEmail()).isPresent()) {
            sql.append("and p.email like :email ");
            params.put("email", dto.getEmail() + "%");
        }

        if (Optional.ofNullable(dto.getStatus()).isPresent()) {
            sql.append("and p.status = :status ");
            params.put("status", dto.getStatus());
        }

        if (Optional.ofNullable(dto.getRole()).isPresent()) {
            sql.append("and p.role = :role ");
            params.put("role", dto.getRole());
        }

        if (Optional.ofNullable(dto.getCreatedDate()).isPresent()) {
            if (dto.getCreatedDate()) {
                sql.append("order by p.createdDate desc ");
            } else {
                sql.append("order by p.createdDate asc ");
            }
        } else {
            sql.append("order by p.createdDate asc ");
        }

        Query query = entityManager.createQuery(sql.toString(), ProfileInfoMapper.class);

        params.forEach(query::setParameter);

        return query.getResultList();
    }
}
