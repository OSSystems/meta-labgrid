DESCRIPTION = "Embedded systems control library for development, testing and installation"
HOMEPAGE = "https://github.com/labgrid-project"
DEPENDS += "python3-setuptools-scm-native"
DEPENDS += "python3-pytest-runner-native"

LICENSE = "LGPL-2.1+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c0e9407a08421b8c72f578433434f0bd"

SRC_URI = " \
    git://github.com/labgrid-project/labgrid.git;branch=master \
    file://configuration.yaml \
    file://labgrid-exporter.service \
    file://environment \
    "
SRCREV = "d11a5ecc86decd74d3ff86b6fb8b730c9ea4a962"

S = "${WORKDIR}/git"

inherit setuptools3 systemd

RDEPENDS_${PN} += " \
    ser2net \
    libgpiod \
    ${PYTHON_PN}-ansicolors \
    ${PYTHON_PN}-attrs \
    ${PYTHON_PN}-asyncio \
    ${PYTHON_PN}-autobahn \
    ${PYTHON_PN}-jinja2 \
    ${PYTHON_PN}-multiprocessing \
    ${PYTHON_PN}-pexpect \
    ${PYTHON_PN}-pyserial \
    ${PYTHON_PN}-pytest \
    ${PYTHON_PN}-pyudev \
    ${PYTHON_PN}-pyusb \
    ${PYTHON_PN}-pyyaml \
    ${PYTHON_PN}-requests \
    ${PYTHON_PN}-xmodem \
    ${PYTHON_PN}-graphviz \
"

SYSTEMD_SERVICE_${PN} = "labgrid-exporter.service"

do_install_append() {
    install -d ${D}${sysconfdir}/labgrid
    install -m 0644 ${WORKDIR}/configuration.yaml ${D}${sysconfdir}/labgrid
    install -m 0644 ${WORKDIR}/environment ${D}${sysconfdir}/labgrid
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/labgrid-exporter.service ${D}${systemd_system_unitdir}/
}

FILES_${PN} += "${sysconfdir} ${systemd_system_unitdir}"

BBCLASSEXTEND = "native nativesdk"
