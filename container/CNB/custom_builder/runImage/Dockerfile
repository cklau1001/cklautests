# https://buildpacks.io/docs/for-platform-operators/how-to/build-inputs/create-builder/run-base/
# To add unzip, fontconfig to the run image.
#
FROM index.docker.io/paketobuildpacks/run-jammy-base:latest

USER root
RUN apt-get update && apt-get install -y unzip fontconfig fonts-dejavu fonts-liberation && rm -rf /var/lib/apt/lists/*

ARG cnb_uid=1002
ARG cnb_gid=1000

# Default uid and gid for a process running in CNB
USER ${cnb_uid}:${cnb_gid}

# Set required CNB target information
LABEL io.buildpacks.base.distro.name="mybuilder1.runimage"
LABEL io.buildpacks.base.distro.version="1.0"