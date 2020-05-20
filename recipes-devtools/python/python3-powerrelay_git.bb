DESCRIPTION = "REST API to control GPIO chips"
HOMEPAGE = "https://github.com/prevas-dk/labgrid-powerrelay"
DEPENDS += "${PYTHON_PN}-pytest-runner-native"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=20796caa814f193af92c180d146bb7ec"

SRC_URI = " \
    git://github.com/prevas-dk/labgrid-powerrelay.git;protocol=https;branch=master \
    file://labgrid-powerrelay.service \
    "
SRCREV = "60ee3d6a09114732eaf33e6aa3ae37486e146a9b"

S = "${WORKDIR}/git"

inherit setuptools3 systemd

SYSTEMD_SERVICE_${PN} = "labgrid-powerrelay.service"

RDEPENDS_${PN} += " \
    ${PYTHON_PN}-click \
    ${PYTHON_PN}-aiohttp \
    ${PYTHON_PN}-trafaret \
    ${PYTHON_PN}-trafaret-config \
    ${PYTHON_PN}-aiohttp-jinja2 \
    ${PYTHON_PN}-yarl \
    ${PYTHON_PN}-pyyaml \
    ${PYTHON_PN}-multidict \
    ${PYTHON_PN}-pytest \
"

do_install_append() {
    rm -rf "${D}${datadir}"
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/labgrid-powerrelay.service ${D}${systemd_system_unitdir}/
}

FILES_${PN} += "${systemd_system_unitdir}"

BBCLASSEXTEND = "native nativesdk"
