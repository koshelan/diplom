package education.diplom.repository;

import education.diplom.model.ErrorMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorMsgRepository extends JpaRepository<ErrorMsg,Long> {


}
