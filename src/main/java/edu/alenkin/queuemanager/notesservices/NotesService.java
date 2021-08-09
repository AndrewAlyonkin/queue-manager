package edu.alenkin.queuemanager.notesservices;

import edu.alenkin.queuemanager.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public interface NotesService extends JpaRepository<Notification, String> {

}
