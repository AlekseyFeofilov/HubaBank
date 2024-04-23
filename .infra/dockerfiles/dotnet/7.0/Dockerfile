FROM mcr.microsoft.com/dotnet/aspnet:7.0
ARG DLL
WORKDIR /app
COPY bin/publish .
RUN echo "dotnet $DLL" >> entrypoint.sh
EXPOSE 80
ENTRYPOINT ["sh", "entrypoint.sh"]