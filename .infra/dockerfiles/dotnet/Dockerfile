FROM mcr.microsoft.com/dotnet/aspnet:8.0
USER app
WORKDIR /app
COPY bin/publish .
EXPOSE 8080
ENTRYPOINT ["dotnet", "Service.dll"]