package com.bit.envdev.dto;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FileDTO {
    private long itemId;
    private long itemFileId;
    private String itemFileName;
    private String itemFilePath;
    private String itemFileOrigin;
}
