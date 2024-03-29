﻿using System.Text.Json.Serialization;

namespace BFF_client.Api.model.bill
{
    [JsonConverter(typeof(JsonStringEnumConverter))]
    public enum ReasonDto
    {
        TERMINAL,
        LOAN
    }
}