import * as httpFuncs from "httpHelper";

describe("fetchResponseHandler", () => {
  it("returns json if response status is ok", () => {
    const body = { sample: "body" };
    const response = new Response(JSON.stringify(body), { status: 200 });

    return httpFuncs.fetchResponseHandler(response, true).then(value => {
      expect(value).toEqual(body);
    });
  });

  it("returns text if response status is ok", () => {
    const body = "sample body";
    const response = new Response(body, { status: 201 });

    return httpFuncs.fetchResponseHandler(response, false).then(value => {
      expect(value).toEqual(body);
    });
  });

  it("throws Error if request unexpectedly fails", () => {
    const response = new Response("sample body", {
      status: 500,
      statusText: "serious failure"
    });

    expect(() => {
      httpFuncs.fetchResponseHandler(response);
    }).toThrow("serious failure: 500");
  });
});

describe("simpleFetchResponseHandler", () => {
  it("returns response status is ok", () => {
    const response = new Response(undefined, { status: 200 });

    expect(httpFuncs.simpleFetchResponseHandler(response)).toBeTruthy();
  });
});
