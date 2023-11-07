FROM ubuntu:latest
RUN apt-get update && apt-get install -y nfs-kernel-server
COPY exports /etc/exports
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
