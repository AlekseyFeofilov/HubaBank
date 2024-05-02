FROM mcr.microsoft.com/dotnet/aspnet:7.0 AS base
WORKDIR /app
EXPOSE 8080
EXPOSE 8082

FROM mcr.microsoft.com/dotnet/sdk:7.0 AS build
ARG BUILD_CONFIGURATION=Release
WORKDIR /src
COPY ["EmployeeGateway/EmployeeGateway.csproj", "EmployeeGateway/"]
COPY ["EmployeeGateway.BL/EmployeeGateway.BL.csproj", "EmployeeGateway.BL/"]
COPY ["EmployeeGateway.Common/EmployeeGateway.Common.csproj", "EmployeeGateway.Common/"]
COPY ["EmployeeGateway.DAL/EmployeeGateway.DAL.csproj", "EmployeeGateway.DAL/"]

RUN dotnet restore "EmployeeGateway/EmployeeGateway.csproj"

COPY . .
WORKDIR "/src/EmployeeGateway"

RUN dotnet build "EmployeeGateway.csproj" -c Release -o /app/build

FROM build AS publish
ARG BUILD_CONFIGURATION=Release
RUN dotnet publish "EmployeeGateway.csproj" -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "EmployeeGateway.dll"]