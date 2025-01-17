$(document).ready(function() {
    const data = [
        { name: 'John', age: 28, city: 'New York' },
        { name: 'Jane', age: 22, city: 'London' },
        { name: 'Mike', age: 32, city: 'San Francisco' },
        { name: 'Sara', age: 24, city: 'Paris' },
        { name: 'David', age: 29, city: 'Berlin' },
        { name: 'Anna', age: 21, city: 'Rome' },
        { name: 'Paul', age: 35, city: 'Madrid' },
        { name: 'Sophia', age: 27, city: 'Sydney' },
        { name: 'James', age: 30, city: 'Tokyo' },
        { name: 'Laura', age: 33, city: 'Los Angeles' }
    ];

    let currentPage = 1;
    const rowsPerPage = 3;
    let sortedData = [...data];  // 복사본을 만들어서 정렬을 위한 배열로 사용
    let sortDirection = { name: 'asc', age: 'asc', city: 'asc' };  // 초기 정렬 방향

    // 테이블에 데이터 렌더링
    function renderTable() {
        const startIdx = (currentPage - 1) * rowsPerPage;
        const paginatedData = sortedData.slice(startIdx, startIdx + rowsPerPage);
        const tbody = $('#dataTable tbody');
        tbody.empty(); // 테이블 내용 초기화

        paginatedData.forEach((item, idx) => {
            const row = $('<tr></tr>');

            Object.entries(item).forEach(([key, value]) => {
                row.append(`<td>${value}</td>`);
            });

            // Action buttons (Up / Down)
            const actionTd = $('<td></td>');
            const buttonGroup = $('<div class="button-group"></div>');

            const upButton = $('<button>Up</button>').click(() => moveRowUp(startIdx + idx));
            const downButton = $('<button>Down</button>').click(() => moveRowDown(startIdx + idx));

            buttonGroup.append(upButton, downButton);
            actionTd.append(buttonGroup);
            row.append(actionTd);

            tbody.append(row);
        });

        updatePagination();
    }

    // 페이지 업데이트
    function updatePagination() {
        const totalPages = Math.ceil(sortedData.length / rowsPerPage);
        $('#totalPages').text(totalPages);
        $('#currentPage').text(currentPage);

        $('#prevPage').prop('disabled', currentPage === 1);
        $('#nextPage').prop('disabled', currentPage === totalPages);
    }

    // 페이지 변경
    $('#prevPage').click(function() {
        if (currentPage > 1) {
            currentPage--;
            renderTable();
        }
    });

    $('#nextPage').click(function() {
        const totalPages = Math.ceil(sortedData.length / rowsPerPage);
        if (currentPage < totalPages) {
            currentPage++;
            renderTable();
        }
    });

    // 행을 위로 이동
    function moveRowUp(index) {
        if (index <= 0) return;  // 첫 번째 행은 위로 이동 불가
        [sortedData[index], sortedData[index - 1]] = [sortedData[index - 1], sortedData[index]];  // 배열에서 두 항목을 교환
        renderTable();  // 데이터 갱신
    }

    // 행을 아래로 이동
    function moveRowDown(index) {
        if (index >= sortedData.length - 1) return;  // 마지막 행은 아래로 이동 불가
        [sortedData[index], sortedData[index + 1]] = [sortedData[index + 1], sortedData[index]];  // 배열에서 두 항목을 교환
        renderTable();  // 데이터 갱신
    }

    // 열을 정렬
    function sortTable(column) {
        const direction = sortDirection[column] === 'asc' ? 'desc' : 'asc';
        sortDirection[column] = direction;

        sortedData.sort((a, b) => {
            if (a[column] < b[column]) return direction === 'asc' ? -1 : 1;
            if (a[column] > b[column]) return direction === 'asc' ? 1 : -1;
            return 0;
        });

        $(`th[data-column=${column}]`).toggleClass('asc', direction === 'asc');
        $(`th[data-column=${column}]`).toggleClass('desc', direction === 'desc');

        renderTable(); // 데이터 갱신
    }

    // 헤더 클릭 시 정렬
    $('.sortable').click(function() {
        const column = $(this).data('column');
        sortTable(column);
    });

    // 초기 데이터 렌더링
    renderTable();
});
