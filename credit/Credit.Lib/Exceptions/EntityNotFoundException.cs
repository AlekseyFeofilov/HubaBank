using System.Net;

namespace Credit.Lib.Exceptions;

public class EntityNotFoundException(Guid id)
    : BusinessException($"Entity with id {id} was not found", HttpStatusCode.NotFound);