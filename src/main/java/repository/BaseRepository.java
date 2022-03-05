package repository;

import db.InMemoryDb;

public interface BaseRepository<Entity,Id> {
     InMemoryDb inMemoryDb = InMemoryDb.getInstance();

     /**
      * persists the entity in db and returns an identifier;
      * @param entity
      * @return
      */
     Id persist(Entity entity);

     /**
      *  finds the entity by its identifier
      * @param id
      * @return
      */
     Entity findById(Id id);

}
