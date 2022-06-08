package de.agiehl.boardgame.BoardgameOffersFinder.persistent.notify;

import de.agiehl.boardgame.BoardgameOffersFinder.persistent.EntityType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@Table("MESSAGES")
public class NotifyEntity {

    @Id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    private Long fkId;

    private EntityType fkType;

    private Long messageId;

    private Long chatId;

    private MessageType messageType;

    public enum MessageType {
        IMAGE, TEXT
    }
}
