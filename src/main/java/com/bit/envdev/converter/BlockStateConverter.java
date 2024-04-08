package com.bit.envdev.converter;

import com.bit.envdev.constant.BlockState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class BlockStateConverter implements AttributeConverter<BlockState, Integer> {
    @Override
    public Integer convertToDatabaseColumn(BlockState blockState) {
        return blockState.getLegacyCode();
    }

    @Override
    public BlockState convertToEntityAttribute(Integer i) {
        return BlockState.ofCode(i);
    }
}
