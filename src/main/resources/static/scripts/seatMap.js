function processSeatMap(svgEl, seats, wantingSeatsId, paidSeatsId) {
    for (const seatData of seats) {
        const seatEl = svgEl.getElementById("seat-" + seatData.id);

        seatEl.dataset.id = seatData.id;
        seatEl.dataset.name = seatData.name;
        seatEl.dataset.area = seatData.area;
        seatEl.dataset.areaId = seatData.areaId;
        seatEl.dataset.seatClass = seatData.seatClass;
        seatEl.dataset.seatClassId = seatData.seatClassId;
        seatEl.dataset.price = seatData.price;
        seatEl.dataset.status = seatData.status;
    }

    for (const seatId of wantingSeatsId) {
        const seatEl = svgEl.getElementById("seat-" + seatId);
        seatEl.dataset.status = "WANTING";
    }

    for (const seatId of paidSeatsId) {
        const seatEl = svgEl.getElementById("seat-" + seatId);
        seatEl.dataset.status = "PAID";
    }
}
