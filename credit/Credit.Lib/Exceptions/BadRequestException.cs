using System.Net;

namespace Credit.Lib.Exceptions;

public class BadRequestException(string reason)
    : BusinessException($"Bad request. Reason: {reason}", HttpStatusCode.BadRequest);