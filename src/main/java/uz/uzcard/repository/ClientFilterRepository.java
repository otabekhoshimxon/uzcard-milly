package uz.uzcard.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.uzcard.dto.client.ClientFilterDTO;
import uz.uzcard.entity.ClientEntity;
import uz.uzcard.entity.ProfileEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class ClientFilterRepository {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    public List clientFiter(ClientFilterDTO dto) {

        StringBuilder builder = new StringBuilder();
        builder.append(" select p from client p  ");
        builder.append(" where p.visible = true ");

        if (dto.getPhone() != null) {
            builder.append(" and p.phone = '" + dto.getPhone() + "' ");
        }
  if (dto.getMiddleName() != null) {
            builder.append(" and p.middle_name = '" + dto.getMiddleName() + "' ");
        }

        if (dto.getName() != null) {
            builder.append(" and p.name = '" + dto.getName() + "' ");
        }
        if (dto.getSurname() != null) {
            builder.append(" and p.surname = '" + dto.getSurname() + "' ");
        }
        Query query = entityManager.createNativeQuery(builder.toString(), ClientEntity.class);

        return  query.getResultList();
    }
}
