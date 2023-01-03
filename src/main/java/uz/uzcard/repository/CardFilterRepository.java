package uz.uzcard.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.uzcard.dto.card.CardFilterDTO;
import uz.uzcard.entity.CardEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
@Component
public class CardFilterRepository {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;



    public List getCardInfo(CardFilterDTO dto){



        StringBuilder builder=new StringBuilder();
        builder.append("select cd from card cd ");
        builder.append("where cd.visible=true ");

        if(dto.getPhone()!=null){
            builder.append(" and cd.phone= '"+dto.getPhone()+"'");
        }

        if(dto.getCard_number()!=null){
            builder.append(" and cd.number= '"+dto.getCard_number()+"'");
        }





        Query nativeQuery = entityManager.createNativeQuery(String.valueOf(builder), CardEntity.class);
        return nativeQuery.getResultList();

    }



}
