package com.excavator.boot.openai;

public enum GptModelEnum {
    GPT_3_5_TURBO_0301("gpt-3.5-turbo-0301"),
    GPT_3_5_TURBO("gpt-3.5-turbo");

    private String name;

    GptModelEnum(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
