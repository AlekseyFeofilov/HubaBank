FROM mcr.microsoft.com/dotnet/aspnet:8.0
ARG DLL
USER app
WORKDIR /app
COPY bin/publish .
SHELL ["/bin/bash", "-c"]
RUN apt update
RUN apt -y install wget
RUN echo "dotnet $DLL" >> entrypoint.sh
EXPOSE 8080
ENTRYPOINT ["sh", "entrypoint.sh"]