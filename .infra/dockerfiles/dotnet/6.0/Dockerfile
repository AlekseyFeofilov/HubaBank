FROM mcr.microsoft.com/dotnet/aspnet:6.0
ARG DLL
WORKDIR /app
COPY bin/publish .
RUN echo "dotnet $DLL" >> entrypoint.sh
EXPOSE 8080
ENTRYPOINT ["sh", "entrypoint.sh"]