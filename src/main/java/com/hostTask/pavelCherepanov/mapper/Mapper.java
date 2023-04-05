package com.hostTask.pavelCherepanov.mapper;

public interface Mapper<typeInput, typeOutput> {
    typeOutput map(typeInput input);

    void mapFromInputInOutput(typeInput input, typeOutput output);
}
