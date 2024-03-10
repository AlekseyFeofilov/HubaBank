FROM mcr.microsoft.com/dotnet/aspnet:8.0
USER app
WORKDIR /app
EXPOSE 8080
COPY /app/publish .
ENTRYPOINT ["dotnet", "run"]